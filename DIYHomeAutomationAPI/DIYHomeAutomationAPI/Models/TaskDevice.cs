namespace DIYHomeAutomationAPI.Models
{
    /// <summary>
    /// This class represents a TaskDevice
    /// </summary>
    public class TaskDevice
    {
        /// <summary>
        /// TaskDevice's Id
        /// </summary>
        public int Id { get; set; }

        /// <summary>
        /// Task's Id
        /// </summary>
        public int TaskId { get; set; }

        /// <summary>
        /// Device's Id
        /// </summary>
        public int DeviceId { get; set; }
    }
}
