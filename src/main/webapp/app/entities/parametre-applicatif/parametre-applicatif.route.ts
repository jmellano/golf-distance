import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ParametreApplicatifComponent } from './parametre-applicatif.component';
import { ParametreApplicatifDetailComponent } from './parametre-applicatif-detail.component';
import { ParametreApplicatifPopupComponent } from './parametre-applicatif-dialog.component';
import { ParametreApplicatifDeletePopupComponent } from './parametre-applicatif-delete-dialog.component';

export const parametreApplicatifRoute: Routes = [
    {
        path: 'parametre-applicatif',
        component: ParametreApplicatifComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'golfDistanceApp.parametreApplicatif.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'parametre-applicatif/:id',
        component: ParametreApplicatifDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'golfDistanceApp.parametreApplicatif.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const parametreApplicatifPopupRoute: Routes = [
    {
        path: 'parametre-applicatif-new',
        component: ParametreApplicatifPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'golfDistanceApp.parametreApplicatif.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'parametre-applicatif/:id/edit',
        component: ParametreApplicatifPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'golfDistanceApp.parametreApplicatif.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'parametre-applicatif/:id/delete',
        component: ParametreApplicatifDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'golfDistanceApp.parametreApplicatif.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
