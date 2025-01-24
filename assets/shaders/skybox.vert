#version 330 core

layout (location = 0) in vec3 aPos;

out vec3 TextCoords;

uniform mat4 projection;
uniform mat4 view;

void main() {
    TextCoords = aPos;
    gl_Position = projection * view * vec4(apos, 1.0);
}
