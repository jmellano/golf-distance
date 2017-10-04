import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { TypeReferentiel } from './type-referentiel.model';
import { TypeReferentielService } from './type-referentiel.service';

@Component({
    selector: 'jhi-type-referentiel-detail',
    templateUrl: './type-referentiel-detail.component.html'
})
export class TypeReferentielDetailComponent implements OnInit, OnDestroy {

    typeReferentiel: TypeReferentiel;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private typeReferentielService: TypeReferentielService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTypeReferentiels();
    }

    load(id) {
        this.typeReferentielService.find(id).subscribe((typeReferentiel) => {
            this.typeReferentiel = typeReferentiel;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTypeReferentiels() {
        this.eventSubscriber = this.eventManager.subscribe(
            'typeReferentielListModification',
            (response) => this.load(this.typeReferentiel.id)
        );
    }
}
