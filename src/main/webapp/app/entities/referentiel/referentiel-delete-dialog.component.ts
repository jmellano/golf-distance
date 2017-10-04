import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Referentiel } from './referentiel.model';
import { ReferentielPopupService } from './referentiel-popup.service';
import { ReferentielService } from './referentiel.service';

@Component({
    selector: 'jhi-referentiel-delete-dialog',
    templateUrl: './referentiel-delete-dialog.component.html'
})
export class ReferentielDeleteDialogComponent {

    referentiel: Referentiel;

    constructor(
        private referentielService: ReferentielService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.referentielService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'referentielListModification',
                content: 'Deleted an referentiel'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-referentiel-delete-popup',
    template: ''
})
export class ReferentielDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private referentielPopupService: ReferentielPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.referentielPopupService
                .open(ReferentielDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
