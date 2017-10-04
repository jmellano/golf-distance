import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Referentiel } from './referentiel.model';
import { ReferentielService } from './referentiel.service';

@Component({
    selector: 'jhi-referentiel-detail',
    templateUrl: './referentiel-detail.component.html'
})
export class ReferentielDetailComponent implements OnInit, OnDestroy {

    referentiel: Referentiel;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private referentielService: ReferentielService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInReferentiels();
    }

    load(id) {
        this.referentielService.find(id).subscribe((referentiel) => {
            this.referentiel = referentiel;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInReferentiels() {
        this.eventSubscriber = this.eventManager.subscribe(
            'referentielListModification',
            (response) => this.load(this.referentiel.id)
        );
    }
}
