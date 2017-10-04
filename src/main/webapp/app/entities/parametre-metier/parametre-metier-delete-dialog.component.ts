import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ParametreMetier } from './parametre-metier.model';
import { ParametreMetierPopupService } from './parametre-metier-popup.service';
import { ParametreMetierService } from './parametre-metier.service';

@Component({
    selector: 'jhi-parametre-metier-delete-dialog',
    templateUrl: './parametre-metier-delete-dialog.component.html'
})
export class ParametreMetierDeleteDialogComponent {

    parametreMetier: ParametreMetier;

    constructor(
        private parametreMetierService: ParametreMetierService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.parametreMetierService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'parametreMetierListModification',
                content: 'Deleted an parametreMetier'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-parametre-metier-delete-popup',
    template: ''
})
export class ParametreMetierDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private parametreMetierPopupService: ParametreMetierPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.parametreMetierPopupService
                .open(ParametreMetierDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
