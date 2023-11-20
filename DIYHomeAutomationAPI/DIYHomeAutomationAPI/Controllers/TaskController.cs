﻿using DIYHomeAutomationAPI.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Microsoft.IdentityModel.Tokens;
using NuGet.Protocol.Plugins;
using System.Reflection;

namespace DIYHomeAutomationAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class TaskController : ControllerBase
    {
        // variables
        private readonly SensorDbContext _context;

        public TaskController(SensorDbContext context) => _context = context;

        /// <summary>
        /// This method search in the database all the Tasks.
        /// </summary>
        /// <returns>List with all the Tasks</returns>
        [HttpGet]
        public async Task<ActionResult<IEnumerable<Models.Task>>> GetTasks() =>
            await _context.Tasks.ToListAsync();

        /// <summary>
        /// This method creates a new Task
        /// </summary>
        /// <param name="task">Task Object</param>
        /// <returns>Method Result</returns>
        [HttpPost]
        public async Task<ActionResult<Models.Task>> PostTask(Models.Task task)
        {
            // Verify if the task receibed is not null
            if (task is null)
                return BadRequest();

            // Add the task to an entity entry to insert into the database
            _context.Tasks.Add(task);

            // Try to save the entry to the database
            await _context.SaveChangesAsync();

            // If is successfully then returns the last task Inserted
            return await _context.Tasks.OrderByDescending(t => t.Id).FirstAsync();
        }

        /// <summary>
        /// This method updates an Task
        /// </summary>
        /// <param name="id">Task's Id</param>
        /// <param name="task">Task Object</param>
        /// <returns>Method Result</returns>
        [HttpPut("{id}")]
        public async Task<ActionResult> PutTask(int id, Models.Task task)
        {
            // Verify if the task receibed is not null or if the Task id are the same
            if (task is null || id != task.Id)
                return BadRequest();

            // Put the task as an entry an set the state as modified to update the database
            _context.Entry(task).State = EntityState.Modified;

            // Try to save to database
            await _context.SaveChangesAsync();

            // If is successfully then returns an OK
            return Ok();
        }

        /// <summary>
        /// This method removes an Task from the database
        /// </summary>
        /// <param name="id">Task's Id</param>
        /// <returns>Method Result</returns>
        [HttpDelete("{id}")]
        public async Task<ActionResult> DeleteTask(int id)
        {
            // Verify if the are any Tasks in the database, if not then return BadRequest
            if (_context.Tasks.IsNullOrEmpty())
                return BadRequest();

            // Get the task by the id
            Models.Task? task = await _context.Tasks.FindAsync(id);

            // Send the task to the method DeleteTask
            return await DeleteTask(task);
        }

        /// <summary>
        /// This method removes an Task from the database
        /// </summary>
        /// <param name="task">Task Object</param>
        /// <returns>Method Result</returns>
        [HttpDelete]
        public async Task<ActionResult> DeleteTask(Models.Task? task)
        {
            // Verify if the are any Tasks in the database or if the task is null, if not then return NotFound
            if (_context.Tasks.IsNullOrEmpty() || task is null)
                return NotFound();

            // Put the task as an entry an set the state as remove from database
            _context.Tasks.Remove(task);

            // Try to save to database
            await _context.SaveChangesAsync();

            // If is successfully then returns an OK 
            return Ok();
        }
    }
}
