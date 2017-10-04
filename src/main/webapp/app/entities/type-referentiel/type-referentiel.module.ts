import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GolfDistanceSharedModule } from '../../shared';
import {
    TypeReferentielService,
    TypeReferentielPopupService,
    TypeReferentielComponent,
    TypeReferentielDetailComponent,
    TypeReferentielDialogComponent,
    TypeReferentielPopupComponent,
    TypeReferentielDeletePopupComponent,
    TypeReferentielDeleteDialogComponent,
    typeReferentielRoute,
    typeReferentielPopupRoute,
} from './';

const ENTITY_STATES = [
    ...typeReferentielRoute,
    ...typeReferentielPopupRoute,
];

@NgModule({
    imports: [
        GolfDistanceSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        TypeReferentielComponent,
        TypeReferentielDetailComponent,
        TypeReferentielDialogComponent,
        TypeReferentielDeleteDialogComponent,
        TypeReferentielPopupComponent,
        TypeReferentielDeletePopupComponent,
    ],
    entryComponents: [
        TypeReferentielComponent,
        TypeReferentielDialogComponent,
        TypeReferentielPopupComponent,
        TypeReferentielDeleteDialogComponent,
        TypeReferentielDeletePopupComponent,
    ],
    providers: [
        TypeReferentielService,
        TypeReferentielPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GolfDistanceTypeReferentielModule {}
