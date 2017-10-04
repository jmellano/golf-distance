import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Calibration } from './calibration.model';
import { CalibrationService } from './calibration.service';

@Component({
    selector: 'jhi-calibration-detail',
    templateUrl: './calibration-detail.component.html'
})
export class CalibrationDetailComponent implements OnInit, OnDestroy {

    calibration: Calibration;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private calibrationService: CalibrationService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCalibrations();
    }

    load(id) {
        this.calibrationService.find(id).subscribe((calibration) => {
            this.calibration = calibration;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCalibrations() {
        this.eventSubscriber = this.eventManager.subscribe(
            'calibrationListModification',
            (response) => this.load(this.calibration.id)
        );
    }
}
