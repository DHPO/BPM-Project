import { ActivityAddVO, ActivityVO } from './types/activity';
const superagent = require('superagent');

async function login(username: string, password: string) {
  const agent = new superagent.agent()

  const url = `http://localhost:8080/api/login?username=${username}&password=${password}`

  await agent.post(url)

  return agent;
}

async function getGps(address: string) {
  const url = `https://restapi.amap.com/v3/assistant/inputtips?s=rsv3&key=00085a636239c8938dbef932ffe5c78d&platform=JS&logversion=2.0&csid=8E7D01EA-51FE-4EBD-AC53-155D986207F4&sdkversion=1.4.15&keywords=${address}`;

  return superagent.get(encodeURI(url))
    .then((res) => {
      const result = res.body.tips;
      if (result.length > 0) {
        const gps = result[0].location;
        const [longitude, latitude] = gps.split(',').map((d) => parseFloat(d))
        return {
          longitude,
          latitude,
        }
      }
      throw new Error('not found');
    })
}

function rawToActivity(data: any): ActivityAddVO {
  return {
    name: data.name,
    tags: [data.typename],
    starttime: new Date(data.start_time).valueOf(),
    endtime: new Date(data.end_time).valueOf(),
    registerendtime: new Date(data.sign_end_time).valueOf(),
    registerstarttime: new Date(data.sign_start_time).valueOf(),
    peoplenum: Math.max(parseInt(data.max_member), parseInt(data.member_count)),
    photourl: data.poster,
    descriptionurl: 'http://118.31.77.203:8080/file/U19367ccc350066/ActiProj/Content/1576761524030',
    location: {
      location: '上海交通大学,'+data.location,
      latitude: 31.022383 + Math.random() * 0.02,
      longitude: 121.442359 + Math.random() * 0.02
    }
  }
}

async function fetch(status: number, count: number) {
  const url = `https://tongqu.me/api/act/type?type=0&status=${status}&offset=0&number=${count}&order=hotvalue&desc=true`

  return superagent.get(url)
    .set('User-Agent', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36')
    .then((res) => {
      return res.body.result.acts
    });
}

async function submitActivity(agent: any, activity: ActivityAddVO) {
  const url = 'http://localhost:8080/api/activity/add'

  return agent.post(url).send(activity)
}

async function sleep(time: number) {
  return new Promise((resolve) => {
    setTimeout(resolve, time);
  })
}

async function getActivity(): Promise<ActivityVO[]> {
  const url = 'http://118.31.77.203:8080/Entity/U19367ccc350066/ActiProj/Activity/';

  return superagent.get(url)
    .then((res) => res.body.Activity)
}

function isRegistering(activity: ActivityVO) {
  const start = activity.registerstarttime;
  const end = activity.registerendtime;
  const now = Date.now()
  return now < end && now > start;
}

function isChecking(activity: ActivityVO) {
  const start = activity.starttime;
  const end = activity.endtime;
  const now = Date.now()
  return now < end && now > start;
}

function modifyActivity(activity: ActivityVO): ActivityVO {
  if (isRegistering(activity)) {
    activity.registernum = Math.floor((0.5 + 0.5 * Math.random()) * activity.peoplenum);
  }
  if (isChecking(activity)) {
    activity.attendnum = Math.floor((0.5 + 0.5 * Math.random()) * activity.registernum);
  }
  return activity;
}

async function submitActivityRMP(activity: ActivityVO) {
  const url = `http://118.31.77.203:8080/Entity/U19367ccc350066/ActiProj/Activity/${activity.id}`

  return superagent.put(url)
    .send(activity)
}

async function register(username: string, password: string) {
  const url = `http://localhost:8080/api/register?username=${username}&password=${password}`;

  return superagent.post(encodeURI(url));
}

async function addFriend(agent: any, friendId: number) {
  const url = `http://localhost:8080/api/friend?friendId=${friendId}`

  return agent.post(url)
}

async function getUserList(agent: any) {
  const url = `http://localhost:8080/api/user/find?username=`

  return agent.get(url)
    .then((res) => JSON.parse(res.body.data).User)
}

async function registerActivity(agent: any, activityId: number) {
  const url = `http://localhost:8080/api/activity/register?activityId=${activityId}`

  return agent.post(url)
}

async function checkin(agent: any, activityId: number) {
  const url = `http://localhost:8080/api/activity/checkin?activityId=${activityId}`

  return agent.post(url)
}

async function main() {
  let agent = await login('admin', 'admin');

  // const rawDatas = await fetch(4, 20)

  // const activities = rawDatas.map(rawToActivity);

  // for await (const a of activities) {
  //   try {
  //     await submitActivity(agent, a);
  //   }
  //   catch (err) {
  //     console.log(err)
  //   }
  //   await sleep(5000);
  // }

  // const activities = await getActivity();
  // console.log(`Activity loaded, num: ${activities.length}`)

  // for await (const activity of activities) {
  //   await submitActivityRMP(modifyActivity(activity));
  //   console.log('send')
  //   await sleep(5000);
  // }
  // const alphabet = 'abcdefghijklmnopqrstuvwxyz'

  // const familyNames = '赵钱孙李周吴郑王冯陈褚卫蒋沈韩杨'

  // const users = Array.from({length: 16}).map((_, i) => `${familyNames[i]}cn`)

  // for await (const user of users) {
  //   await register(user, user);
  //   console.log('send')
  //   await sleep(5000);
  // }

  // const users = await getUserList(agent);
  // console.log(`User loaded, num: ${users.length}`)
  // for await (const user of users) {
  //   if (user.username[0] !== 'd') {
  //     await addFriend(agent, user.id);
  //     console.log('send')
  //     await sleep(5000)
  //   }
  // }

  const users = await getUserList(agent);
  console.log(`User loaded, num: ${users.length}`)
  for await (const user of users) {
    try {
      const agent = await login(user.username, user.username);
      await sleep(2000)
      await registerActivity(agent, 1577080981417)
      await sleep(2000)
      await checkin(agent, 1577080981417)
      await sleep(2000)
      console.log('send')
    } catch (err) {}
  }
}

main()