import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Shot } from './shot.model';
import { ShotPopupService } from './shot-popup.service';
import { ShotService } from './shot.service';
import { PlayerClub, PlayerClubService } from '../player-club';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-shot-dialog',
    templateUrl: './shot-dialog.component.html'
})
export class ShotDialogComponent implements OnInit {

    shot: Shot;
    isSaving: boolean;

    playerclubs: PlayerClub[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private shotService: ShotService,
        private playerClubService: PlayerClubService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.playerClubService.query()
            .subscribe((res: ResponseWrapper) => { this.playerclubs = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.shot.id !== undefined) {
            this.subscribeToSaveResponse(
                this.shotService.update(this.shot));
        } else {
            this.subscribeToSaveResponse(
                this.shotService.create(this.shot));
        }
    }

    private subscribeToSaveResponse(result: Observable<Shot>) {
        result.subscribe((res: Shot) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Shot) {
        this.eventManager.broadcast({ name: 'shotListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.alertService.error(error.message, null, null);
    }

    trackPlayerClubById(index: number, item: PlayerClub) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-shot-popup',
    template: ''
})
export class ShotPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private shotPopupService: ShotPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.shotPopupService
                    .open(ShotDialogComponent as Component, params['id']);
            } else {
                this.shotPopupService
                    .open(ShotDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
