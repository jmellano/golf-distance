import { BaseEntity } from './../../shared';

export class Shot implements BaseEntity {
    constructor(
        public id?: number,
        public force?: number,
        public distance?: number,
        public vent?: number,
        public temperature?: number,
        public humidite?: number,
        public pressionair?: number,
        public playerClub?: BaseEntity,
    ) {
    }
}
