import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { ParametreMetier } from './parametre-metier.model';
import { ParametreMetierService } from './parametre-metier.service';

@Component({
    selector: 'jhi-parametre-metier-detail',
    templateUrl: './parametre-metier-detail.component.html'
})
export class ParametreMetierDetailComponent implements OnInit, OnDestroy {

    parametreMetier: ParametreMetier;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private parametreMetierService: ParametreMetierService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInParametreMetiers();
    }

    load(id) {
        this.parametreMetierService.find(id).subscribe((parametreMetier) => {
            this.parametreMetier = parametreMetier;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInParametreMetiers() {
        this.eventSubscriber = this.eventManager.subscribe(
            'parametreMetierListModification',
            (response) => this.load(this.parametreMetier.id)
        );
    }
}
