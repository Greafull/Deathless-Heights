#version 330 core

#ifdef GL_ES
precision mediump float;
#endif

uniform samplerCube u_environmentCubemap;
varying vec3 v_texCoords;

void main() {
    gl_FragColor = textureCube(u_environmentCubemap, v_texCoords);
}
