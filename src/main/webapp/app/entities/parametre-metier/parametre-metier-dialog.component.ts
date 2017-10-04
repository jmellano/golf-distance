import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ParametreMetier } from './parametre-metier.model';
import { ParametreMetierPopupService } from './parametre-metier-popup.service';
import { ParametreMetierService } from './parametre-metier.service';

@Component({
    selector: 'jhi-parametre-metier-dialog',
    templateUrl: './parametre-metier-dialog.component.html'
})
export class ParametreMetierDialogComponent implements OnInit {

    parametreMetier: ParametreMetier;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private parametreMetierService: ParametreMetierService,
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
        if (this.parametreMetier.id !== undefined) {
            this.subscribeToSaveResponse(
                this.parametreMetierService.update(this.parametreMetier));
        } else {
            this.subscribeToSaveResponse(
                this.parametreMetierService.create(this.parametreMetier));
        }
    }

    private subscribeToSaveResponse(result: Observable<ParametreMetier>) {
        result.subscribe((res: ParametreMetier) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: ParametreMetier) {
        this.eventManager.broadcast({ name: 'parametreMetierListModification', content: 'OK'});
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
    selector: 'jhi-parametre-metier-popup',
    template: ''
})
export class ParametreMetierPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private parametreMetierPopupService: ParametreMetierPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.parametreMetierPopupService
                    .open(ParametreMetierDialogComponent as Component, params['id']);
            } else {
                this.parametreMetierPopupService
                    .open(ParametreMetierDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
