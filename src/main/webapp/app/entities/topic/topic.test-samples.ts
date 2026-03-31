import { ITopic, NewTopic } from './topic.model';

export const sampleWithRequiredData: ITopic = {
  id: 18714,
  topicName: 'swordfish',
};

export const sampleWithPartialData: ITopic = {
  id: 23965,
  topicName: 'black-and-white pfft vision',
};

export const sampleWithFullData: ITopic = {
  id: 1471,
  topicName: 'brr gosh and',
};

export const sampleWithNewData: NewTopic = {
  topicName: 'once',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
