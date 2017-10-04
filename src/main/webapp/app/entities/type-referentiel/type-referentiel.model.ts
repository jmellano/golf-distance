import { BaseEntity } from './../../shared';

export class TypeReferentiel implements BaseEntity {
    constructor(
        public id?: number,
        public trfCode?: string,
        public libelle?: string,
        public typeDeReferentiels?: BaseEntity[],
    ) {
    }
}
