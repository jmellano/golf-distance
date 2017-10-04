import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { ParametreApplicatif } from './parametre-applicatif.model';
import { ParametreApplicatifService } from './parametre-applicatif.service';

@Component({
    selector: 'jhi-parametre-applicatif-detail',
    templateUrl: './parametre-applicatif-detail.component.html'
})
export class ParametreApplicatifDetailComponent implements OnInit, OnDestroy {

    parametreApplicatif: ParametreApplicatif;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private parametreApplicatifService: ParametreApplicatifService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInParametreApplicatifs();
    }

    load(id) {
        this.parametreApplicatifService.find(id).subscribe((parametreApplicatif) => {
            this.parametreApplicatif = parametreApplicatif;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInParametreApplicatifs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'parametreApplicatifListModification',
            (response) => this.load(this.parametreApplicatif.id)
        );
    }
}
