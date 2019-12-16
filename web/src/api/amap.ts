import axios from 'axios';

export async function geoSuggestion(input: string) {
  const url = `https://restapi.amap.com/v3/assistant/inputtips?s=rsv3&key=00085a636239c8938dbef932ffe5c78d&platform=JS&logversion=2.0&csid=8E7D01EA-51FE-4EBD-AC53-155D986207F4&sdkversion=1.4.15&keywords=${input}`;

  return axios.get(url)
    .then((res) => {
      return res.data.tips!;
    });
}
