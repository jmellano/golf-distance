import { BaseEntity } from './../../shared';

export class Referentiel implements BaseEntity {
    constructor(
        public id?: number,
        public code?: string,
        public libelle?: string,
        public ordre?: number,
        public typeReferentiel?: BaseEntity,
    ) {
    }
}
