import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { TypeReferentiel } from './type-referentiel.model';
import { TypeReferentielPopupService } from './type-referentiel-popup.service';
import { TypeReferentielService } from './type-referentiel.service';

@Component({
    selector: 'jhi-type-referentiel-dialog',
    templateUrl: './type-referentiel-dialog.component.html'
})
export class TypeReferentielDialogComponent implements OnInit {

    typeReferentiel: TypeReferentiel;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private typeReferentielService: TypeReferentielService,
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
        if (this.typeReferentiel.id !== undefined) {
            this.subscribeToSaveResponse(
                this.typeReferentielService.update(this.typeReferentiel));
        } else {
            this.subscribeToSaveResponse(
                this.typeReferentielService.create(this.typeReferentiel));
        }
    }

    private subscribeToSaveResponse(result: Observable<TypeReferentiel>) {
        result.subscribe((res: TypeReferentiel) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: TypeReferentiel) {
        this.eventManager.broadcast({ name: 'typeReferentielListModification', content: 'OK'});
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
    selector: 'jhi-type-referentiel-popup',
    template: ''
})
export class TypeReferentielPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private typeReferentielPopupService: TypeReferentielPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.typeReferentielPopupService
                    .open(TypeReferentielDialogComponent as Component, params['id']);
            } else {
                this.typeReferentielPopupService
                    .open(TypeReferentielDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
