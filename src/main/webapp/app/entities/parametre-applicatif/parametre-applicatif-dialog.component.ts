import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ParametreApplicatif } from './parametre-applicatif.model';
import { ParametreApplicatifPopupService } from './parametre-applicatif-popup.service';
import { ParametreApplicatifService } from './parametre-applicatif.service';

@Component({
    selector: 'jhi-parametre-applicatif-dialog',
    templateUrl: './parametre-applicatif-dialog.component.html'
})
export class ParametreApplicatifDialogComponent implements OnInit {

    parametreApplicatif: ParametreApplicatif;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private parametreApplicatifService: ParametreApplicatifService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.parametreApplicatif.id !== undefined) {
            this.subscribeToSaveResponse(
                this.parametreApplicatifService.update(this.parametreApplicatif));
        } else {
            this.subscribeToSaveResponse(
                this.parametreApplicatifService.create(this.parametreApplicatif));
        }
    }

    private subscribeToSaveResponse(result: Observable<ParametreApplicatif>) {
        result.subscribe((res: ParametreApplicatif) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: ParametreApplicatif) {
        this.eventManager.broadcast({ name: 'parametreApplicatifListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-parametre-applicatif-popup',
    template: ''
})
export class ParametreApplicatifPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private parametreApplicatifPopupService: ParametreApplicatifPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.parametreApplicatifPopupService
                    .open(ParametreApplicatifDialogComponent as Component, params['id']);
            } else {
                this.parametreApplicatifPopupService
                    .open(ParametreApplicatifDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
