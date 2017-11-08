import {Component, OnInit, OnDestroy} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Subscription} from 'rxjs/Rx';
import {JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService} from 'ng-jhipster';

import {PlayerClub} from './player-club.model';
import {PlayerClubService} from './player-club.service';
import {ITEMS_PER_PAGE, Principal, ResponseWrapper} from '../../shared';
import {PaginationConfig} from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-player-club',
    templateUrl: './player-club.component.html'
})
export class PlayerClubComponent implements OnInit, OnDestroy {
    playerClubs: PlayerClub[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(private playerClubService: PlayerClubService,
                private alertService: JhiAlertService,
                private eventManager: JhiEventManager,
                private principal: Principal) {
    }

    loadAll() {
        const self = this;
        this.principal.identity().then(function(res) {
            self.playerClubService.queryForPlayer(res.playerId).subscribe(
                (resPC: ResponseWrapper) => {
                    self.playerClubs = resPC.json;
                },
                (_res: ResponseWrapper) => self.onError(_res.json)
            );
        });
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPlayerClubs();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: PlayerClub) {
        return item.id;
    }

    registerChangeInPlayerClubs() {
        this.eventSubscriber = this.eventManager.subscribe('playerClubListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
