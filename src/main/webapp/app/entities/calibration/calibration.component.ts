import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { Calibration } from './calibration.model';
import { CalibrationService } from './calibration.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-calibration',
    templateUrl: './calibration.component.html'
})
export class CalibrationComponent implements OnInit, OnDestroy {
calibrations: Calibration[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private calibrationService: CalibrationService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.calibrationService.query().subscribe(
            (res: ResponseWrapper) => {
                this.calibrations = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInCalibrations();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Calibration) {
        return item.id;
    }
    registerChangeInCalibrations() {
        this.eventSubscriber = this.eventManager.subscribe('calibrationListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
