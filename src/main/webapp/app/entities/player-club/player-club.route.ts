import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { PlayerClubComponent } from './player-club.component';
import { PlayerClubDetailComponent } from './player-club-detail.component';
import { PlayerClubPopupComponent } from './player-club-dialog.component';
import { PlayerClubDeletePopupComponent } from './player-club-delete-dialog.component';

export const playerClubRoute: Routes = [
    {
        path: 'player-club',
        component: PlayerClubComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'golfDistanceApp.playerClub.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'player-club/:id',
        component: PlayerClubDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'golfDistanceApp.playerClub.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const playerClubPopupRoute: Routes = [
    {
        path: 'player-club-new',
        component: PlayerClubPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'golfDistanceApp.playerClub.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'player-club/:id/edit',
        component: PlayerClubPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'golfDistanceApp.playerClub.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'player-club/:id/delete',
        component: PlayerClubDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'golfDistanceApp.playerClub.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
