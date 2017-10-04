import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GolfDistanceSharedModule } from '../../shared';
import {
    PlayerClubService,
    PlayerClubPopupService,
    PlayerClubComponent,
    PlayerClubDetailComponent,
    PlayerClubDialogComponent,
    PlayerClubPopupComponent,
    PlayerClubDeletePopupComponent,
    PlayerClubDeleteDialogComponent,
    playerClubRoute,
    playerClubPopupRoute,
} from './';

const ENTITY_STATES = [
    ...playerClubRoute,
    ...playerClubPopupRoute,
];

@NgModule({
    imports: [
        GolfDistanceSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PlayerClubComponent,
        PlayerClubDetailComponent,
        PlayerClubDialogComponent,
        PlayerClubDeleteDialogComponent,
        PlayerClubPopupComponent,
        PlayerClubDeletePopupComponent,
    ],
    entryComponents: [
        PlayerClubComponent,
        PlayerClubDialogComponent,
        PlayerClubPopupComponent,
        PlayerClubDeleteDialogComponent,
        PlayerClubDeletePopupComponent,
    ],
    providers: [
        PlayerClubService,
        PlayerClubPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GolfDistancePlayerClubModule {}
