import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Shot } from './shot.model';
import { ShotPopupService } from './shot-popup.service';
import { ShotService } from './shot.service';

@Component({
    selector: 'jhi-shot-delete-dialog',
    templateUrl: './shot-delete-dialog.component.html'
})
export class ShotDeleteDialogComponent {

    shot: Shot;

    constructor(
        private shotService: ShotService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.shotService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'shotListModification',
                content: 'Deleted an shot'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-shot-delete-popup',
    template: ''
})
export class ShotDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private shotPopupService: ShotPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.shotPopupService
                .open(ShotDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
