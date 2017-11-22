import {Component, OnInit, OnDestroy} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Response} from '@angular/http';

import {Observable} from 'rxjs/Rx';
import {NgbActiveModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {JhiEventManager, JhiAlertService} from 'ng-jhipster';

import {PlayerClub} from './player-club.model';
import {PlayerClubPopupService} from './player-club-popup.service';
import {PlayerClubService} from './player-club.service';
import {Player, PlayerService} from '../player';
import {ResponseWrapper} from '../../shared';
import {Principal} from "../../shared/auth/principal.service";

@Component({
    selector: 'jhi-player-club-dialog',
    templateUrl: './player-club-dialog.component.html'
})
export class PlayerClubDialogComponent implements OnInit {

    playerClub: PlayerClub;
    isSaving: boolean;
    player: Player;

    constructor(private principal: Principal,
                public activeModal: NgbActiveModal,
                private alertService: JhiAlertService,
                private playerClubService: PlayerClubService,
                private playerService: PlayerService,
                private eventManager: JhiEventManager) {
    }

    ngOnInit() {
        const self = this;

        this.isSaving = false;
        this.principal.identity().then(function (res) {
            self.playerService.find(res.playerId).subscribe((player) => {
                self.player = player;
            }, (res: ResponseWrapper) => this.onError(res.json));
        });
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        this.playerClub.player = this.player;
        if (this.playerClub.id !== undefined) {
            this.subscribeToSaveResponse(
                this.playerClubService.update(this.playerClub));
        } else {
            this.subscribeToSaveResponse(
                this.playerClubService.create(this.playerClub));
        }
    }

    private subscribeToSaveResponse(result: Observable<PlayerClub>) {
        result.subscribe((res: PlayerClub) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: PlayerClub) {
        this.eventManager.broadcast({name: 'playerClubListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.alertService.error(error.message, null, null);
    }

    trackPlayerById(index: number, item: Player) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-player-club-popup',
    template: ''
})
export class PlayerClubPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(private route: ActivatedRoute,
                private playerClubPopupService: PlayerClubPopupService) {
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if (params['id']) {
                this.playerClubPopupService
                    .open(PlayerClubDialogComponent as Component, params['id']);
            } else {
                this.playerClubPopupService
                    .open(PlayerClubDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
