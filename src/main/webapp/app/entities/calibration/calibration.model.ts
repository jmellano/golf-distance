import { BaseEntity } from './../../shared';

export class Calibration implements BaseEntity {
    constructor(
        public id?: number,
        public force?: number,
        public average?: number,
        public standardDeviation?: number,
        public playerClub?: BaseEntity,
    ) {
    }
}
