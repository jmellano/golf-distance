import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Shot } from './shot.model';
import { ShotService } from './shot.service';

@Component({
    selector: 'jhi-shot-detail',
    templateUrl: './shot-detail.component.html'
})
export class ShotDetailComponent implements OnInit, OnDestroy {

    shot: Shot;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private shotService: ShotService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInShots();
    }

    load(id) {
        this.shotService.find(id).subscribe((shot) => {
            this.shot = shot;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInShots() {
        this.eventSubscriber = this.eventManager.subscribe(
            'shotListModification',
            (response) => this.load(this.shot.id)
        );
    }
}
