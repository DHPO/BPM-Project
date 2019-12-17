export interface ActivityAddVO {
  descriptionurl: string;
  name: string;
  peoplenum: number;
  starttime: number;
  endtime: number;
  registerstarttime: number;
  registerendtime: number;
  photourl: string;
  location: {
    location: string;
    longitude: number;
    latitude: number;
  };
  tags: string[];
}

export interface ActivityVO {
  attendnum: number;
  id: number;
  organizerid: number;
  registernum: number;
  descriptionurl: string;
  name: string;
  peoplenum: number;
  starttime: number;
  endtime: number;
  registerstarttime: number;
  registerendtime: number;
  photourl: string;
  location: {
    location: string;
    longitude: number;
    latitude: number;
  };
  tags: string[];
}
