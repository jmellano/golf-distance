import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { PlayerClub } from './player-club.model';
import { PlayerClubService } from './player-club.service';

@Component({
    selector: 'jhi-player-club-detail',
    templateUrl: './player-club-detail.component.html'
})
export class PlayerClubDetailComponent implements OnInit, OnDestroy {

    playerClub: PlayerClub;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private playerClubService: PlayerClubService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPlayerClubs();
    }

    load(id) {
        this.playerClubService.find(id).subscribe((playerClub) => {
            this.playerClub = playerClub;
            this.playerClub.player = playerClub.player;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPlayerClubs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'playerClubListModification',
            (response) => this.load(this.playerClub.id)
        );
    }
}
