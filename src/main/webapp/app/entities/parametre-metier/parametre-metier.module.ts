import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GolfDistanceSharedModule } from '../../shared';
import {
    ParametreMetierService,
    ParametreMetierPopupService,
    ParametreMetierComponent,
    ParametreMetierDetailComponent,
    ParametreMetierDialogComponent,
    ParametreMetierPopupComponent,
    ParametreMetierDeletePopupComponent,
    ParametreMetierDeleteDialogComponent,
    parametreMetierRoute,
    parametreMetierPopupRoute,
} from './';

const ENTITY_STATES = [
    ...parametreMetierRoute,
    ...parametreMetierPopupRoute,
];

@NgModule({
    imports: [
        GolfDistanceSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ParametreMetierComponent,
        ParametreMetierDetailComponent,
        ParametreMetierDialogComponent,
        ParametreMetierDeleteDialogComponent,
        ParametreMetierPopupComponent,
        ParametreMetierDeletePopupComponent,
    ],
    entryComponents: [
        ParametreMetierComponent,
        ParametreMetierDialogComponent,
        ParametreMetierPopupComponent,
        ParametreMetierDeleteDialogComponent,
        ParametreMetierDeletePopupComponent,
    ],
    providers: [
        ParametreMetierService,
        ParametreMetierPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GolfDistanceParametreMetierModule {}
