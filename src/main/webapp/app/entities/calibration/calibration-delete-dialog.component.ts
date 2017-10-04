import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Calibration } from './calibration.model';
import { CalibrationPopupService } from './calibration-popup.service';
import { CalibrationService } from './calibration.service';

@Component({
    selector: 'jhi-calibration-delete-dialog',
    templateUrl: './calibration-delete-dialog.component.html'
})
export class CalibrationDeleteDialogComponent {

    calibration: Calibration;

    constructor(
        private calibrationService: CalibrationService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.calibrationService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'calibrationListModification',
                content: 'Deleted an calibration'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-calibration-delete-popup',
    template: ''
})
export class CalibrationDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private calibrationPopupService: CalibrationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.calibrationPopupService
                .open(CalibrationDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
