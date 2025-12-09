package main

import (
	"archive/zip"
	"encoding/json"
	"io"
	"os"
	"path"
	"strings"

	"github.com/magiconair/properties"
)

type Properties struct {
	Version string `properties:"mod_version"`
}

var modules []string = []string{
	"module-1-14-4",
	"module-1-16",
	"module-1-19",
	"module-1-21-5",
}

func main() {
	props, err := properties.LoadFile("gradle.properties", properties.ISO_8859_1)
	if err != nil {
		panic(err)
	}
	var p Properties
	props.Decode(&p)
	version := p.Version
	ver_suffix := "-" + version
	libs := path.Join("build", "libs")
	out, err := os.Create(path.Join(libs, "water-in-nether"+ver_suffix+".jar"))
	if err != nil {
		panic(err)
	}
	w := zip.NewWriter(out)
	var mixins []string
	for _, module := range modules {
		r, err := zip.OpenReader(path.Join(libs, module+ver_suffix+".jar"))
		if err != nil {
			panic(err)
		}
		fr, err := r.Open(module + ".mixins.json")
		if err != nil {
			panic(err)
		}
		var data map[string]any
		if err := json.NewDecoder(fr).Decode(&data); err != nil {
			panic(err)
		}
		fr.Close()
		for _, v := range data["mixins"].([]any) {
			s := v.(string)
			mixins = append(mixins, s)
		}
		for _, f := range r.File {
			if f.FileInfo().IsDir() {
				continue
			}
			name := f.Name
			if strings.HasPrefix(name, "water_in_nether/mixin") {
				fr, err := f.Open()
				if err != nil {
					panic(err)
				}
				fw, err := w.Create(name)
				if err != nil {
					panic(err)
				}
				io.Copy(fw, fr)
			}
		}
	}

	r, err := zip.OpenReader(path.Join(libs, "main"+ver_suffix+".jar"))
	if err != nil {
		panic(err)
	}
	var data map[string]any
	fr, err := r.Open("water_in_nether.mixins.json")
	if err != nil {
		panic(err)
	}
	d := json.NewDecoder(fr)
	if err := d.Decode(&data); err != nil {
		panic(err)
	}
	fr.Close()
	for _, v := range data["mixins"].([]any) {
		s := v.(string)
		mixins = append(mixins, s)
	}
	data["mixins"] = mixins
	bytes, err := json.Marshal(data)
	if err != nil {
		panic(err)
	}
	fw, err := w.Create("water_in_nether.mixins.json")
	if err != nil {
		panic(err)
	}
	fw.Write(bytes)
	for _, f := range r.File {
		if f.FileInfo().IsDir() {
			continue
		}
		name := f.Name
		if name == "water_in_nether.mixins.json" {
			continue
		}
		fr, err := f.Open()
		if err != nil {
			panic(err)
		}
		fw, err := w.Create(name)
		if err != nil {
			panic(err)
		}
		io.Copy(fw, fr)
		fr.Close()
	}

	w.Close()
}
