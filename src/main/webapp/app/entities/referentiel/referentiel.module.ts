import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GolfDistanceSharedModule } from '../../shared';
import {
    ReferentielService,
    ReferentielPopupService,
    ReferentielComponent,
    ReferentielDetailComponent,
    ReferentielDialogComponent,
    ReferentielPopupComponent,
    ReferentielDeletePopupComponent,
    ReferentielDeleteDialogComponent,
    referentielRoute,
    referentielPopupRoute,
} from './';

const ENTITY_STATES = [
    ...referentielRoute,
    ...referentielPopupRoute,
];

@NgModule({
    imports: [
        GolfDistanceSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ReferentielComponent,
        ReferentielDetailComponent,
        ReferentielDialogComponent,
        ReferentielDeleteDialogComponent,
        ReferentielPopupComponent,
        ReferentielDeletePopupComponent,
    ],
    entryComponents: [
        ReferentielComponent,
        ReferentielDialogComponent,
        ReferentielPopupComponent,
        ReferentielDeleteDialogComponent,
        ReferentielDeletePopupComponent,
    ],
    providers: [
        ReferentielService,
        ReferentielPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GolfDistanceReferentielModule {}
