import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TypeReferentiel } from './type-referentiel.model';
import { TypeReferentielPopupService } from './type-referentiel-popup.service';
import { TypeReferentielService } from './type-referentiel.service';

@Component({
    selector: 'jhi-type-referentiel-delete-dialog',
    templateUrl: './type-referentiel-delete-dialog.component.html'
})
export class TypeReferentielDeleteDialogComponent {

    typeReferentiel: TypeReferentiel;

    constructor(
        private typeReferentielService: TypeReferentielService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.typeReferentielService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'typeReferentielListModification',
                content: 'Deleted an typeReferentiel'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-type-referentiel-delete-popup',
    template: ''
})
export class TypeReferentielDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private typeReferentielPopupService: TypeReferentielPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.typeReferentielPopupService
                .open(TypeReferentielDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
