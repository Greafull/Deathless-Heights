#version 330 core

attribute vec3 a_position;
uniform mat4 u_projTrans;
varying vec3 v_texCoords;

void main() {
    v_texCoords = a_position; // Pass position for cubemap sampling
    gl_Position = u_projTrans * vec4(a_position, 1.0);
}
