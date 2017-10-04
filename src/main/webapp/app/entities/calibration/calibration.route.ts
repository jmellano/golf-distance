import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { CalibrationComponent } from './calibration.component';
import { CalibrationDetailComponent } from './calibration-detail.component';
import { CalibrationPopupComponent } from './calibration-dialog.component';
import { CalibrationDeletePopupComponent } from './calibration-delete-dialog.component';

export const calibrationRoute: Routes = [
    {
        path: 'calibration',
        component: CalibrationComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'golfDistanceApp.calibration.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'calibration/:id',
        component: CalibrationDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'golfDistanceApp.calibration.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const calibrationPopupRoute: Routes = [
    {
        path: 'calibration-new',
        component: CalibrationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'golfDistanceApp.calibration.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'calibration/:id/edit',
        component: CalibrationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'golfDistanceApp.calibration.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'calibration/:id/delete',
        component: CalibrationDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'golfDistanceApp.calibration.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
