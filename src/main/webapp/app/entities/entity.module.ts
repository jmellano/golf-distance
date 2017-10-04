import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { GolfDistanceTypeReferentielModule } from './type-referentiel/type-referentiel.module';
import { GolfDistanceReferentielModule } from './referentiel/referentiel.module';
import { GolfDistanceParametreApplicatifModule } from './parametre-applicatif/parametre-applicatif.module';
import { GolfDistanceParametreMetierModule } from './parametre-metier/parametre-metier.module';
import { GolfDistancePlayerModule } from './player/player.module';
import { GolfDistancePlayerClubModule } from './player-club/player-club.module';
import { GolfDistanceCalibrationModule } from './calibration/calibration.module';
import { GolfDistanceShotModule } from './shot/shot.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        GolfDistanceTypeReferentielModule,
        GolfDistanceReferentielModule,
        GolfDistanceParametreApplicatifModule,
        GolfDistanceParametreMetierModule,
        GolfDistancePlayerModule,
        GolfDistancePlayerClubModule,
        GolfDistanceCalibrationModule,
        GolfDistanceShotModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GolfDistanceEntityModule {}
