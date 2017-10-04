import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PlayerClub } from './player-club.model';
import { PlayerClubPopupService } from './player-club-popup.service';
import { PlayerClubService } from './player-club.service';

@Component({
    selector: 'jhi-player-club-delete-dialog',
    templateUrl: './player-club-delete-dialog.component.html'
})
export class PlayerClubDeleteDialogComponent {

    playerClub: PlayerClub;

    constructor(
        private playerClubService: PlayerClubService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.playerClubService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'playerClubListModification',
                content: 'Deleted an playerClub'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-player-club-delete-popup',
    template: ''
})
export class PlayerClubDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private playerClubPopupService: PlayerClubPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.playerClubPopupService
                .open(PlayerClubDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
