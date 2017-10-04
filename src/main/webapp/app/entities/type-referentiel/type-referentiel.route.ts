import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { TypeReferentielComponent } from './type-referentiel.component';
import { TypeReferentielDetailComponent } from './type-referentiel-detail.component';
import { TypeReferentielPopupComponent } from './type-referentiel-dialog.component';
import { TypeReferentielDeletePopupComponent } from './type-referentiel-delete-dialog.component';

export const typeReferentielRoute: Routes = [
    {
        path: 'type-referentiel',
        component: TypeReferentielComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'golfDistanceApp.typeReferentiel.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'type-referentiel/:id',
        component: TypeReferentielDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'golfDistanceApp.typeReferentiel.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const typeReferentielPopupRoute: Routes = [
    {
        path: 'type-referentiel-new',
        component: TypeReferentielPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'golfDistanceApp.typeReferentiel.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'type-referentiel/:id/edit',
        component: TypeReferentielPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'golfDistanceApp.typeReferentiel.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'type-referentiel/:id/delete',
        component: TypeReferentielDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'golfDistanceApp.typeReferentiel.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
