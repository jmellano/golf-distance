import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { GolfDistanceTypeReferentielModule } from './type-referentiel/type-referentiel.module';
import { GolfDistanceReferentielModule } from './referentiel/referentiel.module';
import { GolfDistanceParametreApplicatifModule } from './parametre-applicatif/parametre-applicatif.module';
import { GolfDistanceParametreMetierModule } from './parametre-metier/parametre-metier.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        GolfDistanceTypeReferentielModule,
        GolfDistanceReferentielModule,
        GolfDistanceParametreApplicatifModule,
        GolfDistanceParametreMetierModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GolfDistanceEntityModule {}
