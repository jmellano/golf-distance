import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GolfDistanceSharedModule } from '../../shared';
import {
    CalibrationService,
    CalibrationPopupService,
    CalibrationComponent,
    CalibrationDetailComponent,
    CalibrationDialogComponent,
    CalibrationPopupComponent,
    CalibrationDeletePopupComponent,
    CalibrationDeleteDialogComponent,
    calibrationRoute,
    calibrationPopupRoute,
} from './';

const ENTITY_STATES = [
    ...calibrationRoute,
    ...calibrationPopupRoute,
];

@NgModule({
    imports: [
        GolfDistanceSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CalibrationComponent,
        CalibrationDetailComponent,
        CalibrationDialogComponent,
        CalibrationDeleteDialogComponent,
        CalibrationPopupComponent,
        CalibrationDeletePopupComponent,
    ],
    entryComponents: [
        CalibrationComponent,
        CalibrationDialogComponent,
        CalibrationPopupComponent,
        CalibrationDeleteDialogComponent,
        CalibrationDeletePopupComponent,
    ],
    providers: [
        CalibrationService,
        CalibrationPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GolfDistanceCalibrationModule {}
