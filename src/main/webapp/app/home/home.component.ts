import {Component, OnInit} from '@angular/core';
import {ModalDismissReasons, NgbModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';

import {JhiAlertService, JhiEventManager} from 'ng-jhipster';

import {PlayerClubService} from '../entities/player-club/player-club.service';
import {ShotService} from '../entities/shot/shot.service';
import {PlayerClub} from '../entities/player-club/player-club.model';

import {Account, LoginModalService, Principal, ResponseWrapper} from '../shared';
import {Shot} from '../entities/shot/shot.model';
import {Observable} from 'rxjs/Observable';

const DarkSkyApi = require('dark-sky-api');

@Component({
    selector: 'jhi-home',
    templateUrl: './home.component.html',
    styleUrls: [
        'home.css'
    ]

})
export class HomeComponent implements OnInit {
    account: Account;
    modalRef: NgbModalRef;
    playerClubs: PlayerClub[];
    playerId: number;
    closeResult: string;
    forceDisponible: any[];

    isSaving: boolean;
    shot: Shot;
    location: any;
    modalAddShot: NgbModalRef;
    isCollapsed = new Array() as Array<boolean>;
    weatherIcon: string;

    constructor(private principal: Principal,
                private alertService: JhiAlertService,
                private loginModalService: LoginModalService,
                private modalService: NgbModal,
                private eventManager: JhiEventManager,
                private playerClubService: PlayerClubService,
                private shotService: ShotService) {
    }

    loadAll() {
        const self = this;
        DarkSkyApi.apiKey = '21de450fc07aea999406249dac64e8ee';
        DarkSkyApi.units = 'si'; // default 'us'
        DarkSkyApi.language = 'fr'; // default 'en'

        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(position => {
                self.location = position.coords;
                const positionDarkSky = {
                    latitude: self.location.latitude,
                    longitude: self.location.longitude
                };
                DarkSkyApi.loadCurrent(positionDarkSky)
                    .then(
                        result => {
                            console.log(result);
                            self.shot.temperature = result.temperature;
                            self.shot.humidite = result.humidity;
                            self.shot.pressionair = result.pressure;
                            self.weatherIcon = result.icon;

                        });                // Retrieve weather information from coordinates (Sydney, Australia)
            });
        }
        this.principal.identity().then(function (res) {
            self.playerId = res.playerId;
            self.forceDisponible = [{id: '0.25', label: '25%'},
                {id: '0.50', label: '50%'},
                {id: '0.75', label: '75%'},
                {id: '0.90', label: '90%'},
                {id: '1.00', label: '100%'}];
            self.playerClubService.queryForPlayer(self.playerId).subscribe(
                (resPC: ResponseWrapper) => {
                    self.playerClubs = resPC.json;
                    self.playerClubs.forEach((club, index) => {
                        if (index === 0) {
                            self.isCollapsed.push(false)
                        } else {
                            self.isCollapsed.push(true);
                        }
                    })
                },
                (_res: ResponseWrapper) => self.onError(_res.json)
            );
        });
    }

    open(content) {
        this.modalAddShot = this.modalService.open(content);
        this.modalAddShot.result.then((result) => {
            this.closeResult = `Closed with: ${result}`;
        }, (reason) => {
            this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
        });

    }

    addShot() {
        this.subscribeToSaveResponse(
            this.shotService.create(this.shot));
        this.registerAddShotSuccess();
    }

    private subscribeToSaveResponse(result: Observable<Shot>) {
        result.subscribe((res: Shot) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Shot) {
        this.eventManager.broadcast({name: 'shotListModification', content: 'OK'});
        this.isSaving = false;
        this.shot.distance = 0;
        this.modalAddShot.close();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private getDismissReason(reason: any): string {
        if (reason === ModalDismissReasons.ESC) {
            return 'by pressing ESC';
        } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
            return 'by clicking on a backdrop';
        } else {
            return `with: ${reason}`;
        }
    }

    ngOnInit() {
        this.loadAll();
        this.shot = new Shot();
        this.shot.playerClub = new PlayerClub();

        this.principal.identity().then((account) => {
            this.account = account;
        });
        this.registerAuthenticationSuccess();
    }

    registerAddShotSuccess() {
        this.eventManager.subscribe('shotListModification', (message) => {
            this.loadAll();
        });
    }

    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', (message) => {
            this.principal.identity().then((account) => {
                this.account = account;
            });
        });
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
