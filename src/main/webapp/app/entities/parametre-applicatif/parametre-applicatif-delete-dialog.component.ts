import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ParametreApplicatif } from './parametre-applicatif.model';
import { ParametreApplicatifPopupService } from './parametre-applicatif-popup.service';
import { ParametreApplicatifService } from './parametre-applicatif.service';

@Component({
    selector: 'jhi-parametre-applicatif-delete-dialog',
    templateUrl: './parametre-applicatif-delete-dialog.component.html'
})
export class ParametreApplicatifDeleteDialogComponent {

    parametreApplicatif: ParametreApplicatif;

    constructor(
        private parametreApplicatifService: ParametreApplicatifService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.parametreApplicatifService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'parametreApplicatifListModification',
                content: 'Deleted an parametreApplicatif'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-parametre-applicatif-delete-popup',
    template: ''
})
export class ParametreApplicatifDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private parametreApplicatifPopupService: ParametreApplicatifPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.parametreApplicatifPopupService
                .open(ParametreApplicatifDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
