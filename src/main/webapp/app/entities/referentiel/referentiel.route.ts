import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ReferentielComponent } from './referentiel.component';
import { ReferentielDetailComponent } from './referentiel-detail.component';
import { ReferentielPopupComponent } from './referentiel-dialog.component';
import { ReferentielDeletePopupComponent } from './referentiel-delete-dialog.component';

export const referentielRoute: Routes = [
    {
        path: 'referentiel',
        component: ReferentielComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'golfDistanceApp.referentiel.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'referentiel/:id',
        component: ReferentielDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'golfDistanceApp.referentiel.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const referentielPopupRoute: Routes = [
    {
        path: 'referentiel-new',
        component: ReferentielPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'golfDistanceApp.referentiel.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'referentiel/:id/edit',
        component: ReferentielPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'golfDistanceApp.referentiel.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'referentiel/:id/delete',
        component: ReferentielDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'golfDistanceApp.referentiel.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
