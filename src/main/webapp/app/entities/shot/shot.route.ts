import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ShotComponent } from './shot.component';
import { ShotDetailComponent } from './shot-detail.component';
import { ShotPopupComponent } from './shot-dialog.component';
import { ShotDeletePopupComponent } from './shot-delete-dialog.component';

export const shotRoute: Routes = [
    {
        path: 'shot',
        component: ShotComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'golfDistanceApp.shot.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'shot/:id',
        component: ShotDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'golfDistanceApp.shot.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const shotPopupRoute: Routes = [
    {
        path: 'shot-new',
        component: ShotPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'golfDistanceApp.shot.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'shot/:id/edit',
        component: ShotPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'golfDistanceApp.shot.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'shot/:id/delete',
        component: ShotDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'golfDistanceApp.shot.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
