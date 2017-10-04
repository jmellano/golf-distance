import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Referentiel } from './referentiel.model';
import { ReferentielPopupService } from './referentiel-popup.service';
import { ReferentielService } from './referentiel.service';
import { TypeReferentiel, TypeReferentielService } from '../type-referentiel';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-referentiel-dialog',
    templateUrl: './referentiel-dialog.component.html'
})
export class ReferentielDialogComponent implements OnInit {

    referentiel: Referentiel;
    isSaving: boolean;

    typereferentiels: TypeReferentiel[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private referentielService: ReferentielService,
        private typeReferentielService: TypeReferentielService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.typeReferentielService.query()
            .subscribe((res: ResponseWrapper) => { this.typereferentiels = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.referentiel.id !== undefined) {
            this.subscribeToSaveResponse(
                this.referentielService.update(this.referentiel));
        } else {
            this.subscribeToSaveResponse(
                this.referentielService.create(this.referentiel));
        }
    }

    private subscribeToSaveResponse(result: Observable<Referentiel>) {
        result.subscribe((res: Referentiel) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Referentiel) {
        this.eventManager.broadcast({ name: 'referentielListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.alertService.error(error.message, null, null);
    }

    trackTypeReferentielById(index: number, item: TypeReferentiel) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-referentiel-popup',
    template: ''
})
export class ReferentielPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private referentielPopupService: ReferentielPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.referentielPopupService
                    .open(ReferentielDialogComponent as Component, params['id']);
            } else {
                this.referentielPopupService
                    .open(ReferentielDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
