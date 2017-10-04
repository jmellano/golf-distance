import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Calibration } from './calibration.model';
import { CalibrationPopupService } from './calibration-popup.service';
import { CalibrationService } from './calibration.service';
import { PlayerClub, PlayerClubService } from '../player-club';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-calibration-dialog',
    templateUrl: './calibration-dialog.component.html'
})
export class CalibrationDialogComponent implements OnInit {

    calibration: Calibration;
    isSaving: boolean;

    playerclubs: PlayerClub[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private calibrationService: CalibrationService,
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
        if (this.calibration.id !== undefined) {
            this.subscribeToSaveResponse(
                this.calibrationService.update(this.calibration));
        } else {
            this.subscribeToSaveResponse(
                this.calibrationService.create(this.calibration));
        }
    }

    private subscribeToSaveResponse(result: Observable<Calibration>) {
        result.subscribe((res: Calibration) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Calibration) {
        this.eventManager.broadcast({ name: 'calibrationListModification', content: 'OK'});
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
    selector: 'jhi-calibration-popup',
    template: ''
})
export class CalibrationPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private calibrationPopupService: CalibrationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.calibrationPopupService
                    .open(CalibrationDialogComponent as Component, params['id']);
            } else {
                this.calibrationPopupService
                    .open(CalibrationDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
