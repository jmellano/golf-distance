import {Component, OnInit} from '@angular/core';
import {NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {JhiEventManager, JhiAlertService} from 'ng-jhipster';

import {CalibrationService} from '../entities/calibration/calibration.service';
import {Calibration} from '../entities/calibration/calibration.model';

import {PlayerClubService} from '../entities/player-club/player-club.service';
import {PlayerClub} from '../entities/player-club/player-club.model';

import {ResponseWrapper, Account, LoginModalService, Principal} from '../shared';

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
    calibrations: Calibration[];

    constructor(private principal: Principal,
                private alertService: JhiAlertService,
                private loginModalService: LoginModalService,
                private eventManager: JhiEventManager,
                private calibrationService: CalibrationService,
                private playerClubService: PlayerClubService) {
    }

    loadAll() {
        const self = this;
        self.calibrations = [];
        this.principal.identity().then(function (res) {
            self.playerClubService.queryForPlayer(res.playerId).subscribe(
                (resPC: ResponseWrapper) => {
                    self.playerClubs = resPC.json;
                    self.playerClubs.forEach((club, index) => {
                        self.calibrationService.queryForPlayerClub(club.id).subscribe(
                            (resCS: ResponseWrapper) => {
                                self.calibrations[index] = resCS.json;
                            })
                    });
                },
                (_res: ResponseWrapper) => self.onError(_res.json)
            );
        });

    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.account = account;
        });
        this.registerAuthenticationSuccess();
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
