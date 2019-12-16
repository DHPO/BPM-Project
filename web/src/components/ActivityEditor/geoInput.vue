<template>
  <div id="wrapper">
    <el-input :value="address" @focus="show"></el-input>
    <el-dialog ref="dialog" id="dialog" :visible.sync="visible">
      <el-form label-width="100px">
        <el-form-item class="clear" label="地址">
          <div class="el-input el-input--medium">
            <input class="el-input__inner" id="tipinput" placeholder="请输入地址"/>
          </div>
        </el-form-item>
        <el-form-item class="clear" label="地图" style="height: 300px">
          <div id="map">
            <el-amap
              ref="map"
              :amap-manager="amapManager"/>
          </div>
        </el-form-item>
        <el-form-item label="">
          <el-button size="small" type="primary" :disabled="!submitable" @click="submit">确定</el-button>
          <el-button size="small" @click="visible=false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script lang="ts">
// @ts-ignore
import { AMapManager, lazyAMapApiLoaderInstance } from 'vue-amap';
import { Component, Vue, Ref } from 'vue-property-decorator';

const amapManager = new AMapManager();

declare const AMap: any;

@Component({})
export default class GeoInput extends Vue {
  @Ref('dialog')
  private dialog!: any;

  private amapManager = amapManager;

  private auto: any;
  private placeSearch: any;

  private visible = false;

  private location: any = null;
  private address: string = '';

  public show() {
    this.visible = true;
  }

  get submitable() {
    return this.location;
  }

  private submit() {
    this.$emit('submit', {
      location: this.address,
      ...this.location,
    });
    this.visible = false;
  }

  private mounted() {
    this.dialog.open = () => {
      lazyAMapApiLoaderInstance.load().then(() => {
        this.auto = new AMap.Autocomplete({
          input: 'tipinput',
        });

        AMap.event.addListener(this.auto, 'select', (e: any) => {
          if (!this.placeSearch) {
            const map = this.amapManager.getMap();
            if (map) {
              this.placeSearch = new AMap.PlaceSearch({
                map,
              });
            }
          }
          if (this.placeSearch) {
            this.placeSearch.setCity(e.poi.adcode);
            this.placeSearch.search(e.poi.name);
            this.location = {
              longitude: e.poi.location.lng,
              latitude: e.poi.location.lat,
            };
            this.address = e.poi.name;
          }
        });
      });
    };
  }
}
</script>

<style scoped>
#map {
  height: 300px;
  width: 500px;
  border-radius: 20px;
  overflow: hidden;
  box-shadow: 0 0 5px #AAAAAA;
  margin-left: auto;
  margin-right: auto;
}

.clear {
  clear: both;
  width: 80%;
}

input#tipinput {
  width: 480px !important;
}
</style>

<style>
.auto-item, .amap-sug-result {
  z-index: 3000!important;
}

#dialog .el-dialog__body {
  height: 60vh;
}
</style>
