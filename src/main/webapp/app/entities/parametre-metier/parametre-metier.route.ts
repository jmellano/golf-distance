import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ParametreMetierComponent } from './parametre-metier.component';
import { ParametreMetierDetailComponent } from './parametre-metier-detail.component';
import { ParametreMetierPopupComponent } from './parametre-metier-dialog.component';
import { ParametreMetierDeletePopupComponent } from './parametre-metier-delete-dialog.component';

export const parametreMetierRoute: Routes = [
    {
        path: 'parametre-metier',
        component: ParametreMetierComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'golfDistanceApp.parametreMetier.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'parametre-metier/:id',
        component: ParametreMetierDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'golfDistanceApp.parametreMetier.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const parametreMetierPopupRoute: Routes = [
    {
        path: 'parametre-metier-new',
        component: ParametreMetierPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'golfDistanceApp.parametreMetier.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'parametre-metier/:id/edit',
        component: ParametreMetierPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'golfDistanceApp.parametreMetier.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'parametre-metier/:id/delete',
        component: ParametreMetierDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'golfDistanceApp.parametreMetier.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
