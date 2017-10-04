import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GolfDistanceSharedModule } from '../../shared';
import {
    ShotService,
    ShotPopupService,
    ShotComponent,
    ShotDetailComponent,
    ShotDialogComponent,
    ShotPopupComponent,
    ShotDeletePopupComponent,
    ShotDeleteDialogComponent,
    shotRoute,
    shotPopupRoute,
} from './';

const ENTITY_STATES = [
    ...shotRoute,
    ...shotPopupRoute,
];

@NgModule({
    imports: [
        GolfDistanceSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ShotComponent,
        ShotDetailComponent,
        ShotDialogComponent,
        ShotDeleteDialogComponent,
        ShotPopupComponent,
        ShotDeletePopupComponent,
    ],
    entryComponents: [
        ShotComponent,
        ShotDialogComponent,
        ShotPopupComponent,
        ShotDeleteDialogComponent,
        ShotDeletePopupComponent,
    ],
    providers: [
        ShotService,
        ShotPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GolfDistanceShotModule {}
