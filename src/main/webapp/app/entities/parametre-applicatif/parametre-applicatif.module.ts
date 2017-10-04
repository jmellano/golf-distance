import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GolfDistanceSharedModule } from '../../shared';
import {
    ParametreApplicatifService,
    ParametreApplicatifPopupService,
    ParametreApplicatifComponent,
    ParametreApplicatifDetailComponent,
    ParametreApplicatifDialogComponent,
    ParametreApplicatifPopupComponent,
    ParametreApplicatifDeletePopupComponent,
    ParametreApplicatifDeleteDialogComponent,
    parametreApplicatifRoute,
    parametreApplicatifPopupRoute,
} from './';

const ENTITY_STATES = [
    ...parametreApplicatifRoute,
    ...parametreApplicatifPopupRoute,
];

@NgModule({
    imports: [
        GolfDistanceSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ParametreApplicatifComponent,
        ParametreApplicatifDetailComponent,
        ParametreApplicatifDialogComponent,
        ParametreApplicatifDeleteDialogComponent,
        ParametreApplicatifPopupComponent,
        ParametreApplicatifDeletePopupComponent,
    ],
    entryComponents: [
        ParametreApplicatifComponent,
        ParametreApplicatifDialogComponent,
        ParametreApplicatifPopupComponent,
        ParametreApplicatifDeleteDialogComponent,
        ParametreApplicatifDeletePopupComponent,
    ],
    providers: [
        ParametreApplicatifService,
        ParametreApplicatifPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GolfDistanceParametreApplicatifModule {}
