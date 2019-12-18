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
  addtime: number;
  attendnum: number;
  descriptionurl: string;
  endtime: number;
  id: number;
  location: string;
  name: string;
  organizerid: number;
  peoplenum: number;
  photourl: string;
  registerendtime: number;
  registernum: number;
  registerstarttime: number;
  starttime: number;
  status: number;
  updatetime: number;
}

export interface ActivityDetailVO {
  activity: ActivityVO;
  position: {
    location: string;
    longitude: number;
    latitude: number;
  };
  tags: string[];
}
